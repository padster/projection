{
  "package": "com.padsterprogramming.tracks.model",

  // An entity that produces a published feed.
  "Artist": {
    // Full ID, of the form :type/sub-id
    "id": "string",
    // Per-artist single payload.
    "meta": "ArtistMeta",
    // Published entities for the artist.
    "entries": "StringMap<Entry>"
    // TODO: Feed index of entries by time.
  },

  // Single struct of data about a single artist.
  "ArtistMeta": {
    "name": "string",
    // Details (ID, url, ...) of the artist in external APIs.
    "externalInfo": "StringMap<ArtistExternal>",
    // TODO: Native DateTime support?
    "lastPoll": "long"
  },

  // Single item published by an artist, a user can get notified on new ones of these.
  "Entry": {
    // Sub-artist ID. Full ID would be :type/artist-sub-id/entries/entry-sub-id
    "id": "string",
    // Time the entry was published.
    "published": "long",
    // Display name for the entry
    "name": "string",
    // Details (ID, url, ...) for the entry in external APIs.
    "externalInfo": "StringMap<EntryExternal>"
    // TODO: Have the artist ID here? Project it on retrieval from the DB?
  },

  // Per-API details for each artist.
  "ArtistExternal": {
    "id": "string",
    "url": "string"
  },

  // Per-API details for each entry.
  // TODO - support extension, e.g. "EntryExternal(External)": {... or whatever.
  "EntryExternal": {
    "id": "string",
    "url": "string"
  }
}
