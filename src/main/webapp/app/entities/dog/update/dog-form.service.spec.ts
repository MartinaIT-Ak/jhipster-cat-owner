import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dog.test-samples';

import { DogFormService } from './dog-form.service';

describe('Dog Form Service', () => {
  let service: DogFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DogFormService);
  });

  describe('Service methods', () => {
    describe('createDogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            race: expect.any(Object),
            age: expect.any(Object),
            healthStatus: expect.any(Object),
            owner: expect.any(Object),
            veterinary: expect.any(Object),
          }),
        );
      });

      it('passing IDog should create a new form with FormGroup', () => {
        const formGroup = service.createDogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            race: expect.any(Object),
            age: expect.any(Object),
            healthStatus: expect.any(Object),
            owner: expect.any(Object),
            veterinary: expect.any(Object),
          }),
        );
      });
    });

    describe('getDog', () => {
      it('should return NewDog for default Dog initial value', () => {
        const formGroup = service.createDogFormGroup(sampleWithNewData);

        const dog = service.getDog(formGroup) as any;

        expect(dog).toMatchObject(sampleWithNewData);
      });

      it('should return NewDog for empty Dog initial value', () => {
        const formGroup = service.createDogFormGroup();

        const dog = service.getDog(formGroup) as any;

        expect(dog).toMatchObject({});
      });

      it('should return IDog', () => {
        const formGroup = service.createDogFormGroup(sampleWithRequiredData);

        const dog = service.getDog(formGroup) as any;

        expect(dog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDog should not enable id FormControl', () => {
        const formGroup = service.createDogFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDog should disable id FormControl', () => {
        const formGroup = service.createDogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
