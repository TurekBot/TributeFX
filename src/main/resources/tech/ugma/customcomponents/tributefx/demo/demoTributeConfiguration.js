var tribute = new Tribute({
    values: [
      {key: 'Peeta Mellark', value: 'peetm'},
      {key: 'Cinna', value: 'cinnastyles'},
      {key: 'Rue', value: 'rue74'},
      {key: 'Foxface', value: 'Foxyweapons'},
      {key: 'Cato', value: 'catod2'},
      {key: 'Clove', value: 'clove74'},
      {key: 'Thresh', value: 'tmoney'},
      {key: 'Glimmer', value: 'glimmerofhope'},
      {key: 'Marvel', value: 'MarvelvsDC'},
      {key: 'Effie Trinket', value: 'effetiquette'},
      {key: 'Haymitch Abernathy ', value: 'heymentor'},
      {key: 'Gale Hawthorne', value: 'ghaw'},
      {key: 'Primrose Everdeen', value: 'primhealer'},
      {key: 'Madge Undersee', value: 'madfire'},
      {key: 'President Snow', value: 'panem_master'},
      {key: 'Finnick Odair', value: 'odaircpr'},
      {key: 'Mags', value: 'oldthunder'},
      {key: 'Johanna Mason', value: 'jmtrator'},
      {key: 'Beetee Latier', value: 'wire_guided'},
      {key: 'Wiress', value: 'finisher'},
      {key: 'Enobaria', value: 'blade_tooth'},
    ],
    selectTemplate: function(person) {
      return '<span contenteditable="false" class="mention" name="' + person.original.key +
      '" title="' + person.original.key + '">' + this.current.collection.trigger
      + person.original[this.current.collection.fillAttr] + '</span>'
    },
    allowSpaces: true,
    replaceTextSuffix: '',
  });
  tribute.attach(document.getElementById('tributable-container'));




//var tribute = new Tribute({
//  values: [],
//  selectTemplate: function(person) {
//    return '<span contenteditable="false" id="mention" email="' + person.original.email +
//    '" title="' + person.original.email + '">' + this.current.collection.trigger
//    + person.original[this.current.collection.fillAttr] + '</span>'
//  },
//  allowSpaces: true,
//  replaceTextSuffix: '',
//  fillAttr: 'key'
//});
//
//var container = document.getElementById('tributable-container');
//tribute.attach(container);