export interface IClient {
  id?: number;
  name?: string;
  age?: number;
  phoneNumber?: string;
  email?: string;
  photoContentType?: string;
  photo?: any;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string,
    public age?: number,
    public phoneNumber?: string,
    public email?: string,
    public photoContentType?: string,
    public photo?: any
  ) {}
}
