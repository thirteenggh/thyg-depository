import Utils from './Utils';
import UIStrings from '../constants/UIStrings';

describe('Utils', () => {
  describe('fieldProps', () => {
    it('sets validation errors from save errors', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({
        saveErrors: {
          test: 'error'
        }
      }));

      expect(validationErrors).toBe('error');
    });

    it('ignores validation errors if not touched', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({isTouched: {test: false}}));

      expect(validationErrors).toBeNull();
    });

    it('sets validation errors when touched', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({
        isTouched: {
          test: true
        },
        validationErrors: {
          test: 'error'
        }
      }));

      expect(validationErrors).toBe('error');
    });

    it('prefers validation errors to saveErrors', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({
        isTouched: {
          test: true
        },
        validationErrors: {
          test: 'error'
        },
        saveErrors: {
          test: 'saveError'
        }
      }));

      expect(validationErrors).toBe('error');
    });

    it('uses saveErrors when saveErrorData matches data', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({
        isTouched: {
          test: true
        },
        data: {
          test: 'test'
        },
        saveErrorData: {
          test: 'test'
        },
        saveErrors: {
          test: 'error'
        }
      }));

      expect(validationErrors).toBe('error');
    });

    it('does not use saveErrors when saveErrorData does not match data', () => {
      const {validationErrors} = Utils.fieldProps('test', makeContext({
        isTouched: {
          test: true
        },
        data: {
          test: 'test'
        },
        saveErrorData: {},
        saveErrors: {
          test: 'error'
        }
      }));

      expect(validationErrors).toBe(null);
    });
  });

  describe('nextSortDirection', () => {
    it('defaults to ASC for other fields', () => {
      const sortDirection = Utils.nextSortDirection('name')({
        sortField: '',
        sortDirection: null
      });

      expect(sortDirection).toBe(Utils.ASC);
    });

    it('defaults to ASC for this field', () => {
      const sortDirection = Utils.nextSortDirection('name')({
        sortField: 'name',
        sortDirection: null
      });

      expect(sortDirection).toBe(Utils.ASC);
    });

    it('switches from ASC to DESC for this field', () => {
      const sortDirection = Utils.nextSortDirection('name')({
        sortField: 'name',
        sortDirection: Utils.ASC
      });

      expect(sortDirection).toBe(Utils.DESC);
    });

    it('switches from DESC to ASC for this field', () => {
      const sortDirection = Utils.nextSortDirection('name')({
        sortField: 'name',
        sortDirection: Utils.DESC
      });

      expect(sortDirection).toBe(Utils.ASC);
    });
  });

  describe('sortDataByFieldAndDirection', () => {
    it('sorts ascending', () => {
      const sortedData = Utils.sortDataByFieldAndDirection({
        sortField: 'name',
        sortDirection: Utils.ASC,
        data: [{name: 'c'}, {name: 'a'}, {name: 'b'}]
      });

      expect(sortedData).toStrictEqual([{name: 'a'}, {name: 'b'}, {name: 'c'}]);
    });

    it('sorts descending', () => {
      const sortedData = Utils.sortDataByFieldAndDirection({
        sortField: 'name',
        sortDirection: Utils.DESC,
        data: [{name: 'c'}, {name: 'a'}, {name: 'b'}]
      });

      expect(sortedData).toStrictEqual([{name: 'c'}, {name: 'b'}, {name: 'a'}]);
    });
  });
});

function makeContext({...ctx}) {
  return {
    context: {
      data: {},
      isTouched: {},
      ...ctx
    }
  }
}
