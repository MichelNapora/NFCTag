package com.nfctag.features.stats;

/** Compteurs de fiabilité communs aux différentes statistiques. */
record LocationCounters(long located, long tooFar, Integer rate) {}
