import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cat.test-samples';

import { CatFormService } from './cat-form.service';

describe('Cat Form Service', () => {
  let service: CatFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CatFormService);
  });

  describe('Service methods', () => {
    describe('createCatFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCatFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            race: expect.any(Object),
            age: expect.any(Object),
            healtStatus: expect.any(Object),
            veterinary: expect.any(Object),
            owner: expect.any(Object),
          }),
        );
      });

      it('passing ICat should create a new form with FormGroup', () => {
        const formGroup = service.createCatFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            race: expect.any(Object),
            age: expect.any(Object),
            healtStatus: expect.any(Object),
            veterinary: expect.any(Object),
            owner: expect.any(Object),
          }),
        );
      });
    });

    describe('getCat', () => {
      it('should return NewCat for default Cat initial value', () => {
        const formGroup = service.createCatFormGroup(sampleWithNewData);

        const cat = service.getCat(formGroup) as any;

        expect(cat).toMatchObject(sampleWithNewData);
      });

      it('should return NewCat for empty Cat initial value', () => {
        const formGroup = service.createCatFormGroup();

        const cat = service.getCat(formGroup) as any;

        expect(cat).toMatchObject({});
      });

      it('should return ICat', () => {
        const formGroup = service.createCatFormGroup(sampleWithRequiredData);

        const cat = service.getCat(formGroup) as any;

        expect(cat).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICat should not enable id FormControl', () => {
        const formGroup = service.createCatFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCat should disable id FormControl', () => {
        const formGroup = service.createCatFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
