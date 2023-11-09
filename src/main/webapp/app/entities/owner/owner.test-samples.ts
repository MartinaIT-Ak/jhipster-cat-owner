import { IOwner, NewOwner } from './owner.model';

export const sampleWithRequiredData: IOwner = {
  id: 13841,
};

export const sampleWithPartialData: IOwner = {
  id: 2636,
  lastName: 'Mayert',
};

export const sampleWithFullData: IOwner = {
  id: 1153,
  firstName: 'Roxanne',
  lastName: "D'Amore",
  address: 'unto opposite',
  phoneNumber: 19344,
};

export const sampleWithNewData: NewOwner = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
