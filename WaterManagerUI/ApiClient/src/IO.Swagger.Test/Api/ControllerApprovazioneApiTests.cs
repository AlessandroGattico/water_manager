/* 
 * 
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 */
using System;
using System.IO;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reflection;
using RestSharp;
using NUnit.Framework;

using IO.Swagger.Client;
using IO.Swagger.Api;

namespace IO.Swagger.Test
{
    /// <summary>
    ///  Class for testing ControllerApprovazioneApi
    /// </summary>
    /// <remarks>
    /// This file is automatically generated by Swagger Codegen.
    /// Please update the test case below to test the API endpoint.
    /// </remarks>
    [TestFixture]
    public class ControllerApprovazioneApiTests
    {
        private ControllerApprovazioneApi instance;

        /// <summary>
        /// Setup before each unit test
        /// </summary>
        [SetUp]
        public void Init()
        {
            instance = new ControllerApprovazioneApi();
        }

        /// <summary>
        /// Clean up after each unit test
        /// </summary>
        [TearDown]
        public void Cleanup()
        {

        }

        /// <summary>
        /// Test an instance of ControllerApprovazioneApi
        /// </summary>
        [Test]
        public void InstanceTest()
        {
            // TODO uncomment below to test 'IsInstanceOfType' ControllerApprovazioneApi
            //Assert.IsInstanceOfType(typeof(ControllerApprovazioneApi), instance, "instance is a ControllerApprovazioneApi");
        }

        /// <summary>
        /// Test AddApprovazione
        /// </summary>
        [Test]
        public void AddApprovazioneTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //string body = null;
            //var response = instance.AddApprovazione(body);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test DeleteApprovazione
        /// </summary>
        [Test]
        public void DeleteApprovazioneTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.DeleteApprovazione(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test GetApprovazioneId
        /// </summary>
        [Test]
        public void GetApprovazioneIdTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.GetApprovazioneId(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
    }

}
