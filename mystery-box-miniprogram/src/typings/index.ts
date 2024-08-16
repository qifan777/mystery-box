export type EditMode = "CREATE" | "UPDATE";

export class MyFile {
  name: string;
  path: string;
  status: "ready" | "uploading" | "finish" = "ready";
  file?: File;
}

export class Result<T> {
  code: number;
  success: boolean = false;
  msg: string;
  result: T;
}

export type Method =
  | "OPTIONS"
  | "GET"
  | "HEAD"
  | "POST"
  | "PUT"
  | "DELETE"
  | "TRACE"
  | "CONNECT"
  | "PATCH";
