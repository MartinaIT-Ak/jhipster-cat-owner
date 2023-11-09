import { IOwner } from 'app/entities/owner/owner.model';

export interface IVeterinary {
  id: number;
  title?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  phoneNumber?: number | null;
  owners?: Pick<IOwner, 'id'>[] | null;
}

export type NewVeterinary = Omit<IVeterinary, 'id'> & { id: null };
