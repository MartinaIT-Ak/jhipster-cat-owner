import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DogDetailComponent } from './dog-detail.component';

describe('Dog Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DogDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DogDetailComponent,
              resolve: { dog: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DogDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dog on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DogDetailComponent);

      // THEN
      expect(instance.dog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
