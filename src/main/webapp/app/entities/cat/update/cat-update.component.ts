import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVeterinary } from 'app/entities/veterinary/veterinary.model';
import { VeterinaryService } from 'app/entities/veterinary/service/veterinary.service';
import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { CatService } from '../service/cat.service';
import { ICat } from '../cat.model';
import { CatFormService, CatFormGroup } from './cat-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cat-update',
  templateUrl: './cat-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CatUpdateComponent implements OnInit {
  isSaving = false;
  cat: ICat | null = null;

  veterinariesSharedCollection: IVeterinary[] = [];
  ownersSharedCollection: IOwner[] = [];

  editForm: CatFormGroup = this.catFormService.createCatFormGroup();

  constructor(
    protected catService: CatService,
    protected catFormService: CatFormService,
    protected veterinaryService: VeterinaryService,
    protected ownerService: OwnerService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareVeterinary = (o1: IVeterinary | null, o2: IVeterinary | null): boolean => this.veterinaryService.compareVeterinary(o1, o2);

  compareOwner = (o1: IOwner | null, o2: IOwner | null): boolean => this.ownerService.compareOwner(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cat }) => {
      this.cat = cat;
      if (cat) {
        this.updateForm(cat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cat = this.catFormService.getCat(this.editForm);
    if (cat.id !== null) {
      this.subscribeToSaveResponse(this.catService.update(cat));
    } else {
      this.subscribeToSaveResponse(this.catService.create(cat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICat>>): void {
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

  protected updateForm(cat: ICat): void {
    this.cat = cat;
    this.catFormService.resetForm(this.editForm, cat);

    this.veterinariesSharedCollection = this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(
      this.veterinariesSharedCollection,
      cat.veterinary,
    );
    this.ownersSharedCollection = this.ownerService.addOwnerToCollectionIfMissing<IOwner>(this.ownersSharedCollection, cat.owner);
  }

  protected loadRelationshipsOptions(): void {
    this.veterinaryService
      .query()
      .pipe(map((res: HttpResponse<IVeterinary[]>) => res.body ?? []))
      .pipe(
        map((veterinaries: IVeterinary[]) =>
          this.veterinaryService.addVeterinaryToCollectionIfMissing<IVeterinary>(veterinaries, this.cat?.veterinary),
        ),
      )
      .subscribe((veterinaries: IVeterinary[]) => (this.veterinariesSharedCollection = veterinaries));

    this.ownerService
      .query()
      .pipe(map((res: HttpResponse<IOwner[]>) => res.body ?? []))
      .pipe(map((owners: IOwner[]) => this.ownerService.addOwnerToCollectionIfMissing<IOwner>(owners, this.cat?.owner)))
      .subscribe((owners: IOwner[]) => (this.ownersSharedCollection = owners));
  }
}
