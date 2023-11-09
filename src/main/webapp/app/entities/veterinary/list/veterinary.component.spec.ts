import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VeterinaryService } from '../service/veterinary.service';

import { VeterinaryComponent } from './veterinary.component';

describe('Veterinary Management Component', () => {
  let comp: VeterinaryComponent;
  let fixture: ComponentFixture<VeterinaryComponent>;
  let service: VeterinaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'veterinary', component: VeterinaryComponent }]),
        HttpClientTestingModule,
        VeterinaryComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(VeterinaryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VeterinaryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VeterinaryService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.veterinaries?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to veterinaryService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getVeterinaryIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getVeterinaryIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
