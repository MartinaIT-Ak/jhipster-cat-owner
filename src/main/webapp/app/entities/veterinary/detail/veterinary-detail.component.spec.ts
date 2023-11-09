import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VeterinaryDetailComponent } from './veterinary-detail.component';

describe('Veterinary Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeterinaryDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VeterinaryDetailComponent,
              resolve: { veterinary: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VeterinaryDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load veterinary on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VeterinaryDetailComponent);

      // THEN
      expect(instance.veterinary).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
