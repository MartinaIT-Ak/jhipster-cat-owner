import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VeterinaryService } from '../service/veterinary.service';
import { IVeterinary } from '../veterinary.model';
import { VeterinaryFormService } from './veterinary-form.service';

import { VeterinaryUpdateComponent } from './veterinary-update.component';

describe('Veterinary Management Update Component', () => {
  let comp: VeterinaryUpdateComponent;
  let fixture: ComponentFixture<VeterinaryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let veterinaryFormService: VeterinaryFormService;
  let veterinaryService: VeterinaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VeterinaryUpdateComponent],
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
      .overrideTemplate(VeterinaryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VeterinaryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    veterinaryFormService = TestBed.inject(VeterinaryFormService);
    veterinaryService = TestBed.inject(VeterinaryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const veterinary: IVeterinary = { id: 456 };

      activatedRoute.data = of({ veterinary });
      comp.ngOnInit();

      expect(comp.veterinary).toEqual(veterinary);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVeterinary>>();
      const veterinary = { id: 123 };
      jest.spyOn(veterinaryFormService, 'getVeterinary').mockReturnValue(veterinary);
      jest.spyOn(veterinaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ veterinary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: veterinary }));
      saveSubject.complete();

      // THEN
      expect(veterinaryFormService.getVeterinary).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(veterinaryService.update).toHaveBeenCalledWith(expect.objectContaining(veterinary));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVeterinary>>();
      const veterinary = { id: 123 };
      jest.spyOn(veterinaryFormService, 'getVeterinary').mockReturnValue({ id: null });
      jest.spyOn(veterinaryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ veterinary: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: veterinary }));
      saveSubject.complete();

      // THEN
      expect(veterinaryFormService.getVeterinary).toHaveBeenCalled();
      expect(veterinaryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVeterinary>>();
      const veterinary = { id: 123 };
      jest.spyOn(veterinaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ veterinary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(veterinaryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
