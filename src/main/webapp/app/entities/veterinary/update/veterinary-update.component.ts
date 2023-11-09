import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVeterinary } from '../veterinary.model';
import { VeterinaryService } from '../service/veterinary.service';
import { VeterinaryFormService, VeterinaryFormGroup } from './veterinary-form.service';

@Component({
  standalone: true,
  selector: 'jhi-veterinary-update',
  templateUrl: './veterinary-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VeterinaryUpdateComponent implements OnInit {
  isSaving = false;
  veterinary: IVeterinary | null = null;

  editForm: VeterinaryFormGroup = this.veterinaryFormService.createVeterinaryFormGroup();

  constructor(
    protected veterinaryService: VeterinaryService,
    protected veterinaryFormService: VeterinaryFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ veterinary }) => {
      this.veterinary = veterinary;
      if (veterinary) {
        this.updateForm(veterinary);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const veterinary = this.veterinaryFormService.getVeterinary(this.editForm);
    if (veterinary.id !== null) {
      this.subscribeToSaveResponse(this.veterinaryService.update(veterinary));
    } else {
      this.subscribeToSaveResponse(this.veterinaryService.create(veterinary));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVeterinary>>): void {
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

  protected updateForm(veterinary: IVeterinary): void {
    this.veterinary = veterinary;
    this.veterinaryFormService.resetForm(this.editForm, veterinary);
  }
}
