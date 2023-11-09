import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICat } from '../cat.model';
import { CatService } from '../service/cat.service';

export const catResolve = (route: ActivatedRouteSnapshot): Observable<null | ICat> => {
  const id = route.params['id'];
  if (id) {
    return inject(CatService)
      .find(id)
      .pipe(
        mergeMap((cat: HttpResponse<ICat>) => {
          if (cat.body) {
            return of(cat.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default catResolve;
