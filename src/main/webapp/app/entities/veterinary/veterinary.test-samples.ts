import { IVeterinary, NewVeterinary } from './veterinary.model';

export const sampleWithRequiredData: IVeterinary = {
  id: 9049,
};

export const sampleWithPartialData: IVeterinary = {
  id: 31817,
  title: 'mollycoddle jog before',
  firstName: 'Fannie',
  lastName: 'Hermann',
  phoneNumber: 25085,
};

export const sampleWithFullData: IVeterinary = {
  id: 1547,
  title: 'gadzooks full knot',
  firstName: 'Dannie',
  lastName: 'Rath',
  address: 'sorrow',
  phoneNumber: 25354,
};

export const sampleWithNewData: NewVeterinary = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
