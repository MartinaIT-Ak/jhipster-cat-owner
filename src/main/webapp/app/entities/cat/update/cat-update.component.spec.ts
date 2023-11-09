import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { VeterinaryService } from 'app/entities/veterinary/service/veterinary.service';
import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { ICat } from '../cat.model';
import { CatService } from '../service/cat.service';
import { CatFormService } from './cat-form.service';

import { CatUpdateComponent } from './cat-update.component';

describe('Cat Management Update Component', () => {
  let comp: CatUpdateComponent;
  let fixture: ComponentFixture<CatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let catFormService: CatFormService;
  let catService: CatService;
  let veterinaryService: VeterinaryService;
  let ownerService: OwnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CatUpdateComponent],
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
      .overrideTemplate(CatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    catFormService = TestBed.inject(CatFormService);
    catService = TestBed.inject(CatService);
    veterinaryService = TestBed.inject(VeterinaryService);
    ownerService = TestBed.inject(OwnerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Veterinary query and add missing value', () => {
      const cat: ICat = { id: 456 };
      const veterinary: IVeterinary = { id: 10537 };
      cat.veterinary = veterinary;

      const veterinaryCollection: IVeterinary[] = [{ id: 17172 }];
      jest.spyOn(veterinaryService, 'query').mockReturnValue(of(new HttpResponse({ body: veterinaryCollection })));
      const additionalVeterinaries = [veterinary];
      const expectedCollection: IVeterinary[] = [...additionalVeterinaries, ...veterinaryCollection];
      jest.spyOn(veterinaryService, 'addVeterinaryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cat });
      comp.ngOnInit();

      expect(veterinaryService.query).toHaveBeenCalled();
      expect(veterinaryService.addVeterinaryToCollectionIfMissing).toHaveBeenCalledWith(
        veterinaryCollection,
        ...additionalVeterinaries.map(expect.objectContaining),
      );
      expect(comp.veterinariesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Owner query and add missing value', () => {
      const cat: ICat = { id: 456 };
      const owner: IOwner = { id: 27196 };
      cat.owner = owner;

      const ownerCollection: IOwner[] = [{ id: 15018 }];
      jest.spyOn(ownerService, 'query').mockReturnValue(of(new HttpResponse({ body: ownerCollection })));
      const additionalOwners = [owner];
      const expectedCollection: IOwner[] = [...additionalOwners, ...ownerCollection];
      jest.spyOn(ownerService, 'addOwnerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cat });
      comp.ngOnInit();

      expect(ownerService.query).toHaveBeenCalled();
      expect(ownerService.addOwnerToCollectionIfMissing).toHaveBeenCalledWith(
        ownerCollection,
        ...additionalOwners.map(expect.objectContaining),
      );
      expect(comp.ownersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cat: ICat = { id: 456 };
      const veterinary: IVeterinary = { id: 28330 };
      cat.veterinary = veterinary;
      const owner: IOwner = { id: 27844 };
      cat.owner = owner;

      activatedRoute.data = of({ cat });
      comp.ngOnInit();

      expect(comp.veterinariesSharedCollection).toContain(veterinary);
      expect(comp.ownersSharedCollection).toContain(owner);
      expect(comp.cat).toEqual(cat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICat>>();
      const cat = { id: 123 };
      jest.spyOn(catFormService, 'getCat').mockReturnValue(cat);
      jest.spyOn(catService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cat }));
      saveSubject.complete();

      // THEN
      expect(catFormService.getCat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(catService.update).toHaveBeenCalledWith(expect.objectContaining(cat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICat>>();
      const cat = { id: 123 };
      jest.spyOn(catFormService, 'getCat').mockReturnValue({ id: null });
      jest.spyOn(catService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cat }));
      saveSubject.complete();

      // THEN
      expect(catFormService.getCat).toHaveBeenCalled();
      expect(catService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICat>>();
      const cat = { id: 123 };
      jest.spyOn(catService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(catService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareVeterinary', () => {
      it('Should forward to veterinaryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(veterinaryService, 'compareVeterinary');
        comp.compareVeterinary(entity, entity2);
        expect(veterinaryService.compareVeterinary).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOwner', () => {
      it('Should forward to ownerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ownerService, 'compareOwner');
        comp.compareOwner(entity, entity2);
        expect(ownerService.compareOwner).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
