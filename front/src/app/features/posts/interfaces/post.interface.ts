import { Comment } from "./comment.interface";

export interface Post {
  id:number,
  title:string,
  topic:string,
  author:string,
  content:string,
  createdAt:Date,
  comments?: Comment[]
}
