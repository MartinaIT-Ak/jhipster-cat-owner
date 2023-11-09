import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDog } from '../dog.model';
import { DogService } from '../service/dog.service';

@Component({
  standalone: true,
  templateUrl: './dog-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DogDeleteDialogComponent {
  dog?: IDog;

  constructor(
    protected dogService: DogService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dogService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
