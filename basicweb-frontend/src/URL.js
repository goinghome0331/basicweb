const API_URL = 'http://localhost:8080';
const FILE_URL = API_URL + '/files';
const USER_URL = API_URL + '/users';



export const POST_URL = API_URL + '/posts';
export const SINGUP_URL = API_URL + '/signup';
export const USER_IMAGE_URL = FILE_URL + '/user-image';
export const USER_INFO_URL = USER_URL + '/info';
export const UPDATE_USER_IMAGE_URL = FILE_URL + '/update-user-image';
export const DELETE_USER_IMAGE_URL = FILE_URL + '/delete-user-image';
export const UPDATE_USER_PASSWORD_URL = USER_URL + '/update-password';
export const DELETE_USER_URL = USER_URL + '/delete-user';
export const GET_POSTS_URL = POST_URL + '/get-by-index';
export const GET_COMMENTS_URL = POST_URL + '/comments-by-postId';
export const GET_COMMENT_URL = POST_URL + '/comments';
export const ADD_COMMENT_URL = '/add-comment';
export const ADD_POST_URL = POST_URL+'/add-post';
export const DELETE_COMMENT_URL = '/delete-comment';
export const DELETE_POST_URL = '/delete-post';