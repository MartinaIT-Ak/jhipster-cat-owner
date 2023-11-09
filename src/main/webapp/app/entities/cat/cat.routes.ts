import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CatComponent } from './list/cat.component';
import { CatDetailComponent } from './detail/cat-detail.component';
import { CatUpdateComponent } from './update/cat-update.component';
import CatResolve from './route/cat-routing-resolve.service';

const catRoute: Routes = [
  {
    path: '',
    component: CatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatDetailComponent,
    resolve: {
      cat: CatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatUpdateComponent,
    resolve: {
      cat: CatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatUpdateComponent,
    resolve: {
      cat: CatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default catRoute;
