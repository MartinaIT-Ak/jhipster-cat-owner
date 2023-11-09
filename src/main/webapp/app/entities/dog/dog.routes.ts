import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DogComponent } from './list/dog.component';
import { DogDetailComponent } from './detail/dog-detail.component';
import { DogUpdateComponent } from './update/dog-update.component';
import DogResolve from './route/dog-routing-resolve.service';

const dogRoute: Routes = [
  {
    path: '',
    component: DogComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DogDetailComponent,
    resolve: {
      dog: DogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DogUpdateComponent,
    resolve: {
      dog: DogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DogUpdateComponent,
    resolve: {
      dog: DogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dogRoute;
