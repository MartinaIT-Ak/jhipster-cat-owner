import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVeterinary, NewVeterinary } from '../veterinary.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVeterinary for edit and NewVeterinaryFormGroupInput for create.
 */
type VeterinaryFormGroupInput = IVeterinary | PartialWithRequiredKeyOf<NewVeterinary>;

type VeterinaryFormDefaults = Pick<NewVeterinary, 'id' | 'owners'>;

type VeterinaryFormGroupContent = {
  id: FormControl<IVeterinary['id'] | NewVeterinary['id']>;
  title: FormControl<IVeterinary['title']>;
  firstName: FormControl<IVeterinary['firstName']>;
  lastName: FormControl<IVeterinary['lastName']>;
  address: FormControl<IVeterinary['address']>;
  phoneNumber: FormControl<IVeterinary['phoneNumber']>;
  owners: FormControl<IVeterinary['owners']>;
};

export type VeterinaryFormGroup = FormGroup<VeterinaryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VeterinaryFormService {
  createVeterinaryFormGroup(veterinary: VeterinaryFormGroupInput = { id: null }): VeterinaryFormGroup {
    const veterinaryRawValue = {
      ...this.getFormDefaults(),
      ...veterinary,
    };
    return new FormGroup<VeterinaryFormGroupContent>({
      id: new FormControl(
        { value: veterinaryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(veterinaryRawValue.title),
      firstName: new FormControl(veterinaryRawValue.firstName),
      lastName: new FormControl(veterinaryRawValue.lastName),
      address: new FormControl(veterinaryRawValue.address),
      phoneNumber: new FormControl(veterinaryRawValue.phoneNumber),
      owners: new FormControl(veterinaryRawValue.owners ?? []),
    });
  }

  getVeterinary(form: VeterinaryFormGroup): IVeterinary | NewVeterinary {
    return form.getRawValue() as IVeterinary | NewVeterinary;
  }

  resetForm(form: VeterinaryFormGroup, veterinary: VeterinaryFormGroupInput): void {
    const veterinaryRawValue = { ...this.getFormDefaults(), ...veterinary };
    form.reset(
      {
        ...veterinaryRawValue,
        id: { value: veterinaryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VeterinaryFormDefaults {
    return {
      id: null,
      owners: [],
    };
  }
}
