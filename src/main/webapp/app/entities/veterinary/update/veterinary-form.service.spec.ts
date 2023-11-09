import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../veterinary.test-samples';

import { VeterinaryFormService } from './veterinary-form.service';

describe('Veterinary Form Service', () => {
  let service: VeterinaryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VeterinaryFormService);
  });

  describe('Service methods', () => {
    describe('createVeterinaryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVeterinaryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            address: expect.any(Object),
            phoneNumber: expect.any(Object),
            owners: expect.any(Object),
          }),
        );
      });

      it('passing IVeterinary should create a new form with FormGroup', () => {
        const formGroup = service.createVeterinaryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            address: expect.any(Object),
            phoneNumber: expect.any(Object),
            owners: expect.any(Object),
          }),
        );
      });
    });

    describe('getVeterinary', () => {
      it('should return NewVeterinary for default Veterinary initial value', () => {
        const formGroup = service.createVeterinaryFormGroup(sampleWithNewData);

        const veterinary = service.getVeterinary(formGroup) as any;

        expect(veterinary).toMatchObject(sampleWithNewData);
      });

      it('should return NewVeterinary for empty Veterinary initial value', () => {
        const formGroup = service.createVeterinaryFormGroup();

        const veterinary = service.getVeterinary(formGroup) as any;

        expect(veterinary).toMatchObject({});
      });

      it('should return IVeterinary', () => {
        const formGroup = service.createVeterinaryFormGroup(sampleWithRequiredData);

        const veterinary = service.getVeterinary(formGroup) as any;

        expect(veterinary).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVeterinary should not enable id FormControl', () => {
        const formGroup = service.createVeterinaryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVeterinary should disable id FormControl', () => {
        const formGroup = service.createVeterinaryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
