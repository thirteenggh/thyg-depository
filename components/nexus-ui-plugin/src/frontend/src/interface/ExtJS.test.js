import ExtJS from './ExtJS';

describe('ExtJS', () => {
  describe('setDirtyStatus', () => {
    it('sets the dirty status correctly', () => {
      ExtJS.setDirtyStatus('key', true);

      expect(window.dirty.includes('key')).toEqual(true);
      expect(window.dirty.includes('key2')).toEqual(false);

      // Set key2 twice to ensure that it has the correct value (it was flip-flopping before)
      ExtJS.setDirtyStatus('key2', true);
      ExtJS.setDirtyStatus('key2', true);

      expect(window.dirty.includes('key')).toEqual(true);
      expect(window.dirty.includes('key2')).toEqual(true);

      ExtJS.setDirtyStatus('key', false);

      expect(window.dirty.includes('key')).toEqual(false);
      expect(window.dirty.includes('key2')).toEqual(true);

      ExtJS.setDirtyStatus('key2', false);

      expect(window.dirty).toEqual([]);
    });
  });
});
