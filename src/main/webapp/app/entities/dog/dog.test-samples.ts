import { IDog, NewDog } from './dog.model';

export const sampleWithRequiredData: IDog = {
  id: 32061,
};

export const sampleWithPartialData: IDog = {
  id: 9210,
};

export const sampleWithFullData: IDog = {
  id: 2530,
  name: 'content heartfelt',
  race: 'whenever continually',
  age: 9389,
  healthStatus: 'verbally polished ha',
};

export const sampleWithNewData: NewDog = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
