import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVeterinary } from '../veterinary.model';
import { VeterinaryService } from '../service/veterinary.service';

export const veterinaryResolve = (route: ActivatedRouteSnapshot): Observable<null | IVeterinary> => {
  const id = route.params['id'];
  if (id) {
    return inject(VeterinaryService)
      .find(id)
      .pipe(
        mergeMap((veterinary: HttpResponse<IVeterinary>) => {
          if (veterinary.body) {
            return of(veterinary.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default veterinaryResolve;
