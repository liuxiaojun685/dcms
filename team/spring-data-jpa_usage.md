### 继承层次
* Repository  空接口
* CrudRepository 继承自 Repository
```
S save(S entity);
Iterable<S> save(Iterable<S> entities);
T findOne(ID id);
boolean exists(ID id);
Iterable<T> findAll();
Iterable<T> findAll(Iterable<ID> ids);
long count();
void delete(ID id);
void delete(T entity);
void delete(Iterable<? extends T> entities);
void deleteAll();
```
* PagingAndSortingRepository 继承自 CrudRepository
```
Iterable<T> findAll(Sort sort);
Page<T> findAll(Pageable pageable);
```
* JpaRepository 继承自 PagingAndSortingRepository
```
List<T> findAll();
List<T> findAll(Sort sort);
List<T> findAll(Iterable<ID> ids);
List<S> save(Iterable<S> entities);
void flush();
S saveAndFlush(S entity);
void deleteInBatch(Iterable<T> entities);
void deleteAllInBatch();
T getOne(ID id);
@Override
	<S extends T> List<S> findAll(Example<S> example);
@Override
  <S extends T> List<S> findAll(Example<S> example, Sort sort);
```

### 创建查询的方式
* 直接使用来自JpaRepository、PagingAndSortingRepository、CrudRepository
* 自定义方法。如：findByUsernameAndPassword
```
And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
NotNull --- 与 IsNotNull 等价；
Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
```
* @Query 创建查询			JPQL
* 通过调用 JPA 命名查询语句创建查询。如：findTop5()
