import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { VeterinaryService } from 'app/entities/veterinary/service/veterinary.service';
import { DogService } from '../service/dog.service';
import { IDog } from '../dog.model';
import { DogFormService, DogFormGroup } from './dog-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dog-update',
  templateUrl: './dog-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DogUpdateComponent implements OnInit {
  isSaving = false;
  dog: IDog | null = null;

  ownersSharedCollection: IOwner[] = [];
  veterinariesSharedCollection: IVeterinary[] = [];

  editForm: DogFormGroup = this.dogFormService.createDogFormGroup();

  constructor(
    protected dogService: DogService,
    protected dogFormService: DogFormService,
    protected ownerService: OwnerService,
    protected veterinaryService: VeterinaryService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareOwner = (o1: IOwner | null, o2: IOwner | null): boolean => this.ownerService.compareOwner(o1, o2);

  compareVeterinary = (o1: IVeterinary | null, o2: IVeterinary | null): boolean => this.veterinaryService.compareVeterinary(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dog }) => {
      this.dog = dog;
      if (dog) {
        this.updateForm(dog);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dog = this.dogFormService.getDog(this.editForm);
    if (dog.id !== null) {
      this.subscribeToSaveResponse(this.dogService.update(dog));
    } else {
      this.subscribeToSaveResponse(this.dogService.create(dog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDog>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dog: IDog): void {
    this.dog = dog;
    this.dogFormService.resetForm(this.editForm, dog);

    this.ownersSharedCollection = this.ownerService.addOwnerToCollectionIfMissing<IOwner>(this.ownersSharedCollection, dog.owner);
    this.veterinariesSharedCollection = this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(
      this.veterinariesSharedCollection,
      dog.veterinary,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ownerService
      .query()
      .pipe(map((res: HttpResponse<IOwner[]>) => res.body ?? []))
      .pipe(map((owners: IOwner[]) => this.ownerService.addOwnerToCollectionIfMissing<IOwner>(owners, this.dog?.owner)))
      .subscribe((owners: IOwner[]) => (this.ownersSharedCollection = owners));

    this.veterinaryService
      .query()
      .pipe(map((res: HttpResponse<IVeterinary[]>) => res.body ?? []))
      .pipe(
        map((veterinaries: IVeterinary[]) =>
          this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(veterinaries, this.dog?.veterinary),
        ),
      )
      .subscribe((veterinaries: IVeterinary[]) => (this.veterinariesSharedCollection = veterinaries));
  }
}
