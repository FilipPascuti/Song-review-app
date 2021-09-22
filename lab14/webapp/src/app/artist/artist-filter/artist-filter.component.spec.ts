import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistFilterComponent } from './artist-filter.component';

describe('ArtistFilterComponent', () => {
  let component: ArtistFilterComponent;
  let fixture: ComponentFixture<ArtistFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArtistFilterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
