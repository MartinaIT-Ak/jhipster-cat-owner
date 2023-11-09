import { IVeterinary } from 'app/entities/veterinary/veterinary.model';

export interface IOwner {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  phoneNumber?: number | null;
  veterinaries?: Pick<IVeterinary, 'id'>[] | null;
}

export type NewOwner = Omit<IOwner, 'id'> & { id: null };
