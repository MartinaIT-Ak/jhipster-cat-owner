import { ICat, NewCat } from './cat.model';

export const sampleWithRequiredData: ICat = {
  id: 19751,
};

export const sampleWithPartialData: ICat = {
  id: 10417,
  name: 'declination occlude condescend',
  age: 27566,
  healtStatus: 'ultimately pfft',
};

export const sampleWithFullData: ICat = {
  id: 3183,
  name: 'as',
  race: 'gratefully',
  age: 15473,
  healtStatus: 'whenever mysteriously',
};

export const sampleWithNewData: NewCat = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
