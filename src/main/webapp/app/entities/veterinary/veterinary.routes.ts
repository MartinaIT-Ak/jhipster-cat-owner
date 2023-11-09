import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { VeterinaryComponent } from './list/veterinary.component';
import { VeterinaryDetailComponent } from './detail/veterinary-detail.component';
import { VeterinaryUpdateComponent } from './update/veterinary-update.component';
import VeterinaryResolve from './route/veterinary-routing-resolve.service';

const veterinaryRoute: Routes = [
  {
    path: '',
    component: VeterinaryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VeterinaryDetailComponent,
    resolve: {
      veterinary: VeterinaryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VeterinaryUpdateComponent,
    resolve: {
      veterinary: VeterinaryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VeterinaryUpdateComponent,
    resolve: {
      veterinary: VeterinaryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default veterinaryRoute;
