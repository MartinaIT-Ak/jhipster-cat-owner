import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVeterinary, NewVeterinary } from '../veterinary.model';

export type PartialUpdateVeterinary = Partial<IVeterinary> & Pick<IVeterinary, 'id'>;

export type EntityResponseType = HttpResponse<IVeterinary>;
export type EntityArrayResponseType = HttpResponse<IVeterinary[]>;

@Injectable({ providedIn: 'root' })
export class VeterinaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/veterinaries');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(veterinary: NewVeterinary): Observable<EntityResponseType> {
    return this.http.post<IVeterinary>(this.resourceUrl, veterinary, { observe: 'response' });
  }

  update(veterinary: IVeterinary): Observable<EntityResponseType> {
    return this.http.put<IVeterinary>(`${this.resourceUrl}/${this.getVeterinaryIdentifier(veterinary)}`, veterinary, {
      observe: 'response',
    });
  }

  partialUpdate(veterinary: PartialUpdateVeterinary): Observable<EntityResponseType> {
    return this.http.patch<IVeterinary>(`${this.resourceUrl}/${this.getVeterinaryIdentifier(veterinary)}`, veterinary, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVeterinary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVeterinary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVeterinaryIdentifier(veterinary: Pick<IVeterinary, 'id'>): number {
    return veterinary.id;
  }

  compareVeterinary(o1: Pick<IVeterinary, 'id'> | null, o2: Pick<IVeterinary, 'id'> | null): boolean {
    return o1 && o2 ? this.getVeterinaryIdentifier(o1) === this.getVeterinaryIdentifier(o2) : o1 === o2;
  }

  addVeterinaryToCollectionIfMissing<Type extends Pick<IVeterinary, 'id'>>(
    veterinaryCollection: Type[],
    ...veterinariesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const veterinaries: Type[] = veterinariesToCheck.filter(isPresent);
    if (veterinaries.length > 0) {
      const veterinaryCollectionIdentifiers = veterinaryCollection.map(veterinaryItem => this.getVeterinaryIdentifier(veterinaryItem)!);
      const veterinariesToAdd = veterinaries.filter(veterinaryItem => {
        const veterinaryIdentifier = this.getVeterinaryIdentifier(veterinaryItem);
        if (veterinaryCollectionIdentifiers.includes(veterinaryIdentifier)) {
          return false;
        }
        veterinaryCollectionIdentifiers.push(veterinaryIdentifier);
        return true;
      });
      return [...veterinariesToAdd, ...veterinaryCollection];
    }
    return veterinaryCollection;
  }
}
