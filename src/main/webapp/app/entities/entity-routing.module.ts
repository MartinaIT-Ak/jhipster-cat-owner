import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cat',
        data: { pageTitle: 'archiLogicielApp.cat.home.title' },
        loadChildren: () => import('./cat/cat.routes'),
      },
      {
        path: 'owner',
        data: { pageTitle: 'archiLogicielApp.owner.home.title' },
        loadChildren: () => import('./owner/owner.routes'),
      },
      {
        path: 'veterinary',
        data: { pageTitle: 'archiLogicielApp.veterinary.home.title' },
        loadChildren: () => import('./veterinary/veterinary.routes'),
      },
      {
        path: 'dog',
        data: { pageTitle: 'archiLogicielApp.dog.home.title' },
        loadChildren: () => import('./dog/dog.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
