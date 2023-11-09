import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CatDetailComponent } from './cat-detail.component';

describe('Cat Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CatDetailComponent,
              resolve: { cat: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CatDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load cat on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CatDetailComponent);

      // THEN
      expect(instance.cat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
