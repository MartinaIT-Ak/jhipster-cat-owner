import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICat, NewCat } from '../cat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICat for edit and NewCatFormGroupInput for create.
 */
type CatFormGroupInput = ICat | PartialWithRequiredKeyOf<NewCat>;

type CatFormDefaults = Pick<NewCat, 'id'>;

type CatFormGroupContent = {
  id: FormControl<ICat['id'] | NewCat['id']>;
  name: FormControl<ICat['name']>;
  race: FormControl<ICat['race']>;
  age: FormControl<ICat['age']>;
  healtStatus: FormControl<ICat['healtStatus']>;
  veterinary: FormControl<ICat['veterinary']>;
  owner: FormControl<ICat['owner']>;
};

export type CatFormGroup = FormGroup<CatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CatFormService {
  createCatFormGroup(cat: CatFormGroupInput = { id: null }): CatFormGroup {
    const catRawValue = {
      ...this.getFormDefaults(),
      ...cat,
    };
    return new FormGroup<CatFormGroupContent>({
      id: new FormControl(
        { value: catRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(catRawValue.name),
      race: new FormControl(catRawValue.race),
      age: new FormControl(catRawValue.age),
      healtStatus: new FormControl(catRawValue.healtStatus),
      veterinary: new FormControl(catRawValue.veterinary),
      owner: new FormControl(catRawValue.owner),
    });
  }

  getCat(form: CatFormGroup): ICat | NewCat {
    return form.getRawValue() as ICat | NewCat;
  }

  resetForm(form: CatFormGroup, cat: CatFormGroupInput): void {
    const catRawValue = { ...this.getFormDefaults(), ...cat };
    form.reset(
      {
        ...catRawValue,
        id: { value: catRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CatFormDefaults {
    return {
      id: null,
    };
  }
}
