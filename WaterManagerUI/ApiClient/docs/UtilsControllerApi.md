# IO.Swagger.Api.UtilsControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**CheckCampagnaNome**](UtilsControllerApi.md#checkcampagnanome) | **GET** /api/v1/utils/check/campagnaNome/{id} | 
[**CheckCampoNome**](UtilsControllerApi.md#checkcamponome) | **GET** /api/v1/utils/check/campoNome/{id} | 
[**CheckMail**](UtilsControllerApi.md#checkmail) | **GET** /api/v1/utils/check/email | 
[**CheckUsername**](UtilsControllerApi.md#checkusername) | **GET** /api/v1/utils/check/username | 
[**GetBacini**](UtilsControllerApi.md#getbacini) | **GET** /api/v1/utils/bacino/get/all | 
[**GetEsigenze**](UtilsControllerApi.md#getesigenze) | **GET** /api/v1/utils/esigenza/get/all | 
[**GetIrrigazioni**](UtilsControllerApi.md#getirrigazioni) | **GET** /api/v1/utils/irrigazione/get/all | 
[**GetRaccolti**](UtilsControllerApi.md#getraccolti) | **GET** /api/v1/utils/raccolto/get/all | 
[**GetSensorTypes**](UtilsControllerApi.md#getsensortypes) | **GET** /api/v1/utils/sensorTypes/get/all | 
[**GetTopics**](UtilsControllerApi.md#gettopics) | **GET** /api/v1/utils/topics/get/all | 

<a name="checkcampagnanome"></a>
# **CheckCampagnaNome**
> string CheckCampagnaNome (string param, int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CheckCampagnaNomeExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();
            var param = param_example;  // string | 
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.CheckCampagnaNome(param, id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.CheckCampagnaNome: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 
 **id** | **int?**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="checkcamponome"></a>
# **CheckCampoNome**
> string CheckCampoNome (string param, int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CheckCampoNomeExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();
            var param = param_example;  // string | 
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.CheckCampoNome(param, id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.CheckCampoNome: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 
 **id** | **int?**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="checkmail"></a>
# **CheckMail**
> string CheckMail (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CheckMailExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.CheckMail(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.CheckMail: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="checkusername"></a>
# **CheckUsername**
> string CheckUsername (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CheckUsernameExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.CheckUsername(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.CheckUsername: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getbacini"></a>
# **GetBacini**
> string GetBacini ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetBaciniExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetBacini();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetBacini: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getesigenze"></a>
# **GetEsigenze**
> string GetEsigenze ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetEsigenzeExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetEsigenze();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetEsigenze: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getirrigazioni"></a>
# **GetIrrigazioni**
> string GetIrrigazioni ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetIrrigazioniExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetIrrigazioni();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetIrrigazioni: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getraccolti"></a>
# **GetRaccolti**
> string GetRaccolti ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRaccoltiExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetRaccolti();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetRaccolti: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getsensortypes"></a>
# **GetSensorTypes**
> string GetSensorTypes ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetSensorTypesExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetSensorTypes();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetSensorTypes: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="gettopics"></a>
# **GetTopics**
> string GetTopics ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetTopicsExample
    {
        public void main()
        {
            var apiInstance = new UtilsControllerApi();

            try
            {
                string result = apiInstance.GetTopics();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling UtilsControllerApi.GetTopics: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
