import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDog, NewDog } from '../dog.model';

export type PartialUpdateDog = Partial<IDog> & Pick<IDog, 'id'>;

export type EntityResponseType = HttpResponse<IDog>;
export type EntityArrayResponseType = HttpResponse<IDog[]>;

@Injectable({ providedIn: 'root' })
export class DogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dogs');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(dog: NewDog): Observable<EntityResponseType> {
    return this.http.post<IDog>(this.resourceUrl, dog, { observe: 'response' });
  }

  update(dog: IDog): Observable<EntityResponseType> {
    return this.http.put<IDog>(`${this.resourceUrl}/${this.getDogIdentifier(dog)}`, dog, { observe: 'response' });
  }

  partialUpdate(dog: PartialUpdateDog): Observable<EntityResponseType> {
    return this.http.patch<IDog>(`${this.resourceUrl}/${this.getDogIdentifier(dog)}`, dog, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDog>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDogIdentifier(dog: Pick<IDog, 'id'>): number {
    return dog.id;
  }

  compareDog(o1: Pick<IDog, 'id'> | null, o2: Pick<IDog, 'id'> | null): boolean {
    return o1 && o2 ? this.getDogIdentifier(o1) === this.getDogIdentifier(o2) : o1 === o2;
  }

  addDogToCollectionIfMissing<Type extends Pick<IDog, 'id'>>(dogCollection: Type[], ...dogsToCheck: (Type | null | undefined)[]): Type[] {
    const dogs: Type[] = dogsToCheck.filter(isPresent);
    if (dogs.length > 0) {
      const dogCollectionIdentifiers = dogCollection.map(dogItem => this.getDogIdentifier(dogItem)!);
      const dogsToAdd = dogs.filter(dogItem => {
        const dogIdentifier = this.getDogIdentifier(dogItem);
        if (dogCollectionIdentifiers.includes(dogIdentifier)) {
          return false;
        }
        dogCollectionIdentifiers.push(dogIdentifier);
        return true;
      });
      return [...dogsToAdd, ...dogCollection];
    }
    return dogCollection;
  }
}
