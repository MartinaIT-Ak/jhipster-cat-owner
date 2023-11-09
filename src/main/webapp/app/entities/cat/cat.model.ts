import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { IOwner } from 'app/entities/owner/owner.model';

export interface ICat {
  id: number;
  name?: string | null;
  race?: string | null;
  age?: number | null;
  healtStatus?: string | null;
  veterinary?: Pick<IVeterinary, 'id'> | null;
  owner?: Pick<IOwner, 'id'> | null;
}

export type NewCat = Omit<ICat, 'id'> & { id: null };
