import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { VeterinaryService } from 'app/entities/veterinary/service/veterinary.service';
import { IOwner } from '../owner.model';
import { OwnerService } from '../service/owner.service';
import { OwnerFormService, OwnerFormGroup } from './owner-form.service';

@Component({
  standalone: true,
  selector: 'jhi-owner-update',
  templateUrl: './owner-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OwnerUpdateComponent implements OnInit {
  isSaving = false;
  owner: IOwner | null = null;

  veterinariesSharedCollection: IVeterinary[] = [];

  editForm: OwnerFormGroup = this.ownerFormService.createOwnerFormGroup();

  constructor(
    protected ownerService: OwnerService,
    protected ownerFormService: OwnerFormService,
    protected veterinaryService: VeterinaryService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareVeterinary = (o1: IVeterinary | null, o2: IVeterinary | null): boolean => this.veterinaryService.compareVeterinary(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.owner = owner;
      if (owner) {
        this.updateForm(owner);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const owner = this.ownerFormService.getOwner(this.editForm);
    if (owner.id !== null) {
      this.subscribeToSaveResponse(this.ownerService.update(owner));
    } else {
      this.subscribeToSaveResponse(this.ownerService.create(owner));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOwner>>): void {
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

  protected updateForm(owner: IOwner): void {
    this.owner = owner;
    this.ownerFormService.resetForm(this.editForm, owner);

    this.veterinariesSharedCollection = this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(
      this.veterinariesSharedCollection,
      ...(owner.veterinaries ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.veterinaryService
      .query()
      .pipe(map((res: HttpResponse<IVeterinary[]>) => res.body ?? []))
      .pipe(
        map((veterinaries: IVeterinary[]) =>
          this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(veterinaries, ...(this.owner?.veterinaries ?? [])),
        ),
      )
      .subscribe((veterinaries: IVeterinary[]) => (this.veterinariesSharedCollection = veterinaries));
  }
}
