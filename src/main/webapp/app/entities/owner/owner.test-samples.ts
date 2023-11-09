import { IOwner, NewOwner } from './owner.model';

export const sampleWithRequiredData: IOwner = {
  id: 23384,
};

export const sampleWithPartialData: IOwner = {
  id: 1153,
  lastName: 'Hauck',
};

export const sampleWithFullData: IOwner = {
  id: 5625,
  firstName: 'Kenyatta',
  lastName: 'Mayert',
  address: 'greedily territory',
  phoneNumber: 22623,
};

export const sampleWithNewData: NewOwner = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
