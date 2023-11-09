import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVeterinary } from '../veterinary.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../veterinary.test-samples';

import { VeterinaryService } from './veterinary.service';

const requireRestSample: IVeterinary = {
  ...sampleWithRequiredData,
};

describe('Veterinary Service', () => {
  let service: VeterinaryService;
  let httpMock: HttpTestingController;
  let expectedResult: IVeterinary | IVeterinary[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VeterinaryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Veterinary', () => {
      const veterinary = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(veterinary).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Veterinary', () => {
      const veterinary = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(veterinary).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Veterinary', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Veterinary', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Veterinary', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVeterinaryToCollectionIfMissing', () => {
      it('should add a Veterinary to an empty array', () => {
        const veterinary: IVeterinary = sampleWithRequiredData;
        expectedResult = service.addVeterinaryToCollectionIfMissing([], veterinary);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(veterinary);
      });

      it('should not add a Veterinary to an array that contains it', () => {
        const veterinary: IVeterinary = sampleWithRequiredData;
        const veterinaryCollection: IVeterinary[] = [
          {
            ...veterinary,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVeterinaryToCollectionIfMissing(veterinaryCollection, veterinary);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Veterinary to an array that doesn't contain it", () => {
        const veterinary: IVeterinary = sampleWithRequiredData;
        const veterinaryCollection: IVeterinary[] = [sampleWithPartialData];
        expectedResult = service.addVeterinaryToCollectionIfMissing(veterinaryCollection, veterinary);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(veterinary);
      });

      it('should add only unique Veterinary to an array', () => {
        const veterinaryArray: IVeterinary[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const veterinaryCollection: IVeterinary[] = [sampleWithRequiredData];
        expectedResult = service.addVeterinaryToCollectionIfMissing(veterinaryCollection, ...veterinaryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const veterinary: IVeterinary = sampleWithRequiredData;
        const veterinary2: IVeterinary = sampleWithPartialData;
        expectedResult = service.addVeterinaryToCollectionIfMissing([], veterinary, veterinary2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(veterinary);
        expect(expectedResult).toContain(veterinary2);
      });

      it('should accept null and undefined values', () => {
        const veterinary: IVeterinary = sampleWithRequiredData;
        expectedResult = service.addVeterinaryToCollectionIfMissing([], null, veterinary, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(veterinary);
      });

      it('should return initial array if no Veterinary is added', () => {
        const veterinaryCollection: IVeterinary[] = [sampleWithRequiredData];
        expectedResult = service.addVeterinaryToCollectionIfMissing(veterinaryCollection, undefined, null);
        expect(expectedResult).toEqual(veterinaryCollection);
      });
    });

    describe('compareVeterinary', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVeterinary(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVeterinary(entity1, entity2);
        const compareResult2 = service.compareVeterinary(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVeterinary(entity1, entity2);
        const compareResult2 = service.compareVeterinary(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVeterinary(entity1, entity2);
        const compareResult2 = service.compareVeterinary(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
