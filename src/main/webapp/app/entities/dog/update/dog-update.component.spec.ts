import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { VeterinaryService } from 'app/entities/veterinary/service/veterinary.service';
import { IDog } from '../dog.model';
import { DogService } from '../service/dog.service';
import { DogFormService } from './dog-form.service';

import { DogUpdateComponent } from './dog-update.component';

describe('Dog Management Update Component', () => {
  let comp: DogUpdateComponent;
  let fixture: ComponentFixture<DogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dogFormService: DogFormService;
  let dogService: DogService;
  let ownerService: OwnerService;
  let veterinaryService: VeterinaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DogUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dogFormService = TestBed.inject(DogFormService);
    dogService = TestBed.inject(DogService);
    ownerService = TestBed.inject(OwnerService);
    veterinaryService = TestBed.inject(VeterinaryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Owner query and add missing value', () => {
      const dog: IDog = { id: 456 };
      const owner: IOwner = { id: 13841 };
      dog.owner = owner;

      const ownerCollection: IOwner[] = [{ id: 18673 }];
      jest.spyOn(ownerService, 'query').mockReturnValue(of(new HttpResponse({ body: ownerCollection })));
      const additionalOwners = [owner];
      const expectedCollection: IOwner[] = [...additionalOwners, ...ownerCollection];
      jest.spyOn(ownerService, 'addOwnerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dog });
      comp.ngOnInit();

      expect(ownerService.query).toHaveBeenCalled();
      expect(ownerService.addOwnerToCollectionIfMissing).toHaveBeenCalledWith(
        ownerCollection,
        ...additionalOwners.map(expect.objectContaining),
      );
      expect(comp.ownersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Veterinary query and add missing value', () => {
      const dog: IDog = { id: 456 };
      const veterinary: IVeterinary = { id: 3077 };
      dog.veterinary = veterinary;

      const veterinaryCollection: IVeterinary[] = [{ id: 22348 }];
      jest.spyOn(veterinaryService, 'query').mockReturnValue(of(new HttpResponse({ body: veterinaryCollection })));
      const additionalVeterinaries = [veterinary];
      const expectedCollection: IVeterinary[] = [...additionalVeterinaries, ...veterinaryCollection];
      jest.spyOn(veterinaryService, 'addVeterinaryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dog });
      comp.ngOnInit();

      expect(veterinaryService.query).toHaveBeenCalled();
      expect(veterinaryService.addVeterinaryToCollectionIfMissing).toHaveBeenCalledWith(
        veterinaryCollection,
        ...additionalVeterinaries.map(expect.objectContaining),
      );
      expect(comp.veterinariesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dog: IDog = { id: 456 };
      const owner: IOwner = { id: 2253 };
      dog.owner = owner;
      const veterinary: IVeterinary = { id: 25839 };
      dog.veterinary = veterinary;

      activatedRoute.data = of({ dog });
      comp.ngOnInit();

      expect(comp.ownersSharedCollection).toContain(owner);
      expect(comp.veterinariesSharedCollection).toContain(veterinary);
      expect(comp.dog).toEqual(dog);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDog>>();
      const dog = { id: 123 };
      jest.spyOn(dogFormService, 'getDog').mockReturnValue(dog);
      jest.spyOn(dogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dog }));
      saveSubject.complete();

      // THEN
      expect(dogFormService.getDog).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dogService.update).toHaveBeenCalledWith(expect.objectContaining(dog));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDog>>();
      const dog = { id: 123 };
      jest.spyOn(dogFormService, 'getDog').mockReturnValue({ id: null });
      jest.spyOn(dogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dog: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dog }));
      saveSubject.complete();

      // THEN
      expect(dogFormService.getDog).toHaveBeenCalled();
      expect(dogService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDog>>();
      const dog = { id: 123 };
      jest.spyOn(dogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dogService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOwner', () => {
      it('Should forward to ownerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ownerService, 'compareOwner');
        comp.compareOwner(entity, entity2);
        expect(ownerService.compareOwner).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVeterinary', () => {
      it('Should forward to veterinaryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(veterinaryService, 'compareVeterinary');
        comp.compareVeterinary(entity, entity2);
        expect(veterinaryService.compareVeterinary).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
