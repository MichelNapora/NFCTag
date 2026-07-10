import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Counts, CountsService } from './counts.service';

/** Gabarit du back-office : barre latérale Spi + zone de contenu. */
@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss'
})
export class ShellComponent implements OnInit {

  counts: Counts = { presences: 0, buildings: 0, wings: 0, tags: 0, businesses: 0 };

  constructor(private countsService: CountsService) {}

  ngOnInit(): void {
    this.countsService.counts$.subscribe(c => this.counts = c);
    this.countsService.refresh();
  }
}
