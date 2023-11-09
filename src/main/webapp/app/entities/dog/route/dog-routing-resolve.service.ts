import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDog } from '../dog.model';
import { DogService } from '../service/dog.service';

export const dogResolve = (route: ActivatedRouteSnapshot): Observable<null | IDog> => {
  const id = route.params['id'];
  if (id) {
    return inject(DogService)
      .find(id)
      .pipe(
        mergeMap((dog: HttpResponse<IDog>) => {
          if (dog.body) {
            return of(dog.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default dogResolve;
