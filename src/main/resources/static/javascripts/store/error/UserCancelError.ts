export class UserCancelError implements Error {

  name = 'UserCancelError';

  constructor(public message: string) {
  }
}
