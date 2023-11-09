import { IOwner } from 'app/entities/owner/owner.model';
import { IVeterinary } from 'app/entities/veterinary/veterinary.model';

export interface IDog {
  id: number;
  name?: string | null;
  race?: string | null;
  age?: number | null;
  healthStatus?: string | null;
  owner?: Pick<IOwner, 'id'> | null;
  veterinary?: Pick<IVeterinary, 'id'> | null;
}

export type NewDog = Omit<IDog, 'id'> & { id: null };
