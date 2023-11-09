import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICat, NewCat } from '../cat.model';

export type PartialUpdateCat = Partial<ICat> & Pick<ICat, 'id'>;

export type EntityResponseType = HttpResponse<ICat>;
export type EntityArrayResponseType = HttpResponse<ICat[]>;

@Injectable({ providedIn: 'root' })
export class CatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cats');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(cat: NewCat): Observable<EntityResponseType> {
    return this.http.post<ICat>(this.resourceUrl, cat, { observe: 'response' });
  }

  update(cat: ICat): Observable<EntityResponseType> {
    return this.http.put<ICat>(`${this.resourceUrl}/${this.getCatIdentifier(cat)}`, cat, { observe: 'response' });
  }

  partialUpdate(cat: PartialUpdateCat): Observable<EntityResponseType> {
    return this.http.patch<ICat>(`${this.resourceUrl}/${this.getCatIdentifier(cat)}`, cat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCatIdentifier(cat: Pick<ICat, 'id'>): number {
    return cat.id;
  }

  compareCat(o1: Pick<ICat, 'id'> | null, o2: Pick<ICat, 'id'> | null): boolean {
    return o1 && o2 ? this.getCatIdentifier(o1) === this.getCatIdentifier(o2) : o1 === o2;
  }

  addCatToCollectionIfMissing<Type extends Pick<ICat, 'id'>>(catCollection: Type[], ...catsToCheck: (Type | null | undefined)[]): Type[] {
    const cats: Type[] = catsToCheck.filter(isPresent);
    if (cats.length > 0) {
      const catCollectionIdentifiers = catCollection.map(catItem => this.getCatIdentifier(catItem)!);
      const catsToAdd = cats.filter(catItem => {
        const catIdentifier = this.getCatIdentifier(catItem);
        if (catCollectionIdentifiers.includes(catIdentifier)) {
          return false;
        }
        catCollectionIdentifiers.push(catIdentifier);
        return true;
      });
      return [...catsToAdd, ...catCollection];
    }
    return catCollection;
  }
}
