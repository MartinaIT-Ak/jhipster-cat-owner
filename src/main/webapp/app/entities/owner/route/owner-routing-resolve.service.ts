import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOwner } from '../owner.model';
import { OwnerService } from '../service/owner.service';

export const ownerResolve = (route: ActivatedRouteSnapshot): Observable<null | IOwner> => {
  const id = route.params['id'];
  if (id) {
    return inject(OwnerService)
      .find(id)
      .pipe(
        mergeMap((owner: HttpResponse<IOwner>) => {
          if (owner.body) {
            return of(owner.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default ownerResolve;
