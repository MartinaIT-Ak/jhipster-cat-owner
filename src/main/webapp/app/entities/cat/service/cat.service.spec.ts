import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICat } from '../cat.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cat.test-samples';

import { CatService } from './cat.service';

const requireRestSample: ICat = {
  ...sampleWithRequiredData,
};

describe('Cat Service', () => {
  let service: CatService;
  let httpMock: HttpTestingController;
  let expectedResult: ICat | ICat[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CatService);
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

    it('should create a Cat', () => {
      const cat = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cat', () => {
      const cat = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cat', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cat', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cat', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCatToCollectionIfMissing', () => {
      it('should add a Cat to an empty array', () => {
        const cat: ICat = sampleWithRequiredData;
        expectedResult = service.addCatToCollectionIfMissing([], cat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cat);
      });

      it('should not add a Cat to an array that contains it', () => {
        const cat: ICat = sampleWithRequiredData;
        const catCollection: ICat[] = [
          {
            ...cat,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCatToCollectionIfMissing(catCollection, cat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cat to an array that doesn't contain it", () => {
        const cat: ICat = sampleWithRequiredData;
        const catCollection: ICat[] = [sampleWithPartialData];
        expectedResult = service.addCatToCollectionIfMissing(catCollection, cat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cat);
      });

      it('should add only unique Cat to an array', () => {
        const catArray: ICat[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const catCollection: ICat[] = [sampleWithRequiredData];
        expectedResult = service.addCatToCollectionIfMissing(catCollection, ...catArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cat: ICat = sampleWithRequiredData;
        const cat2: ICat = sampleWithPartialData;
        expectedResult = service.addCatToCollectionIfMissing([], cat, cat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cat);
        expect(expectedResult).toContain(cat2);
      });

      it('should accept null and undefined values', () => {
        const cat: ICat = sampleWithRequiredData;
        expectedResult = service.addCatToCollectionIfMissing([], null, cat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cat);
      });

      it('should return initial array if no Cat is added', () => {
        const catCollection: ICat[] = [sampleWithRequiredData];
        expectedResult = service.addCatToCollectionIfMissing(catCollection, undefined, null);
        expect(expectedResult).toEqual(catCollection);
      });
    });

    describe('compareCat', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCat(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCat(entity1, entity2);
        const compareResult2 = service.compareCat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCat(entity1, entity2);
        const compareResult2 = service.compareCat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCat(entity1, entity2);
        const compareResult2 = service.compareCat(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
