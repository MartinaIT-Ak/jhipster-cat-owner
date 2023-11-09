import { IVeterinary, NewVeterinary } from './veterinary.model';

export const sampleWithRequiredData: IVeterinary = {
  id: 7578,
};

export const sampleWithPartialData: IVeterinary = {
  id: 4551,
  title: 'show ha agenda',
  firstName: 'Laverna',
  lastName: 'Goodwin',
  address: 'truthfully ack',
  phoneNumber: 21474,
};

export const sampleWithFullData: IVeterinary = {
  id: 24120,
  title: 'whose ranch scour',
  firstName: 'Nolan',
  lastName: 'Nicolas',
  address: 'mandate',
  phoneNumber: 9592,
};

export const sampleWithNewData: NewVeterinary = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
