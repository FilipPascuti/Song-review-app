import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistSortedComponent } from './artist-sorted.component';

describe('ArtistSortedComponent', () => {
  let component: ArtistSortedComponent;
  let fixture: ComponentFixture<ArtistSortedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArtistSortedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistSortedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
